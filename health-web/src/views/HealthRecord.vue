<template>
  <div class="health-record-page">
    <!-- 搜索区域 -->
    <el-card shadow="never" class="search-card">
      <el-form :model="queryForm" inline>
        <el-form-item label="记录时间">
          <el-date-picker
            v-model="queryForm.timeRange"
            type="datetimerange"
            range-separator="至"
            start-placeholder="开始时间"
            end-placeholder="结束时间"
            value-format="YYYY-MM-DDTHH:mm:ss"
            style="width: 360px"
          />
        </el-form-item>
        <el-form-item label="BMI区间">
          <el-input-number v-model="queryForm.minBmi" :min="0" :max="50" :step="0.1" placeholder="最小" controls-position="right" />
          <span class="range-sep">-</span>
          <el-input-number v-model="queryForm.maxBmi" :min="0" :max="50" :step="0.1" placeholder="最大" controls-position="right" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" :icon="Search" @click="handleSearch">查询</el-button>
          <el-button :icon="Refresh" @click="handleReset">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <!-- 表格区域 -->
    <el-card shadow="never" class="table-card">
      <div class="toolbar">
        <el-button type="primary" :icon="Plus" @click="handleAdd">新增记录</el-button>
        <el-button type="danger" :icon="Delete" :disabled="!selectedIds.length" @click="handleBatchDelete">
          批量删除
        </el-button>
      </div>

      <el-table
        v-loading="loading"
        :data="tableData"
        border
        stripe
        @selection-change="handleSelectionChange"
        style="width: 100%"
      >
        <el-table-column type="selection" width="50" align="center" />
        <el-table-column prop="id" label="ID" width="70" align="center" />
        <el-table-column prop="height" label="身高(cm)" width="100" align="center" />
        <el-table-column prop="weight" label="体重(kg)" width="100" align="center" />
        <el-table-column prop="bmi" label="BMI" width="80" align="center">
          <template #default="{ row }">
            <el-tag :type="getBmiTagType(row.bmi)" size="small">{{ row.bmi ?? '-' }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="heartRate" label="心率" width="80" align="center" />
        <el-table-column prop="bloodPressure" label="血压" width="100" align="center" />
        <el-table-column prop="exerciseDuration" label="运动(分钟)" width="110" align="center" />
        <el-table-column prop="sleepTime" label="睡眠(小时)" width="110" align="center" />
        <el-table-column prop="dietHabit" label="饮食习惯" min-width="160" show-overflow-tooltip />
        <el-table-column prop="recordTime" label="记录时间" width="170" align="center" />
        <el-table-column label="操作" width="240" align="center" fixed="right">
          <template #default="{ row }">
            <el-button type="primary" link :icon="Edit" @click="handleEdit(row)">编辑</el-button>
            <el-button type="success" link :icon="MagicStick" @click="handleAiSuggest(row)">生成健康建议</el-button>
            <el-button type="danger" link :icon="Delete" @click="handleDelete(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <div class="pagination">
        <el-pagination
          v-model:current-page="pagination.pageNum"
          v-model:page-size="pagination.pageSize"
          :page-sizes="[10, 20, 50]"
          :total="pagination.total"
          layout="total, sizes, prev, pager, next, jumper"
          background
          @size-change="fetchList"
          @current-change="fetchList"
        />
      </div>
    </el-card>

    <!-- 新增/编辑弹窗 -->
    <el-dialog
      v-model="dialogVisible"
      :title="dialogTitle"
      width="600px"
      destroy-on-close
      @closed="resetForm"
    >
      <el-form ref="formRef" :model="form" :rules="rules" label-width="110px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="身高(cm)" prop="height">
              <el-input-number v-model="form.height" :min="50" :max="250" :precision="2" style="width: 100%" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="体重(kg)" prop="weight">
              <el-input-number v-model="form.weight" :min="20" :max="300" :precision="2" style="width: 100%" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="心率(次/分)" prop="heartRate">
              <el-input-number v-model="form.heartRate" :min="30" :max="220" style="width: 100%" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="血压" prop="bloodPressure">
              <el-input v-model="form.bloodPressure" placeholder="如 120/80" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="运动时长(分)" prop="exerciseDuration">
              <el-input-number v-model="form.exerciseDuration" :min="0" :max="1440" style="width: 100%" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="睡眠(小时)" prop="sleepTime">
              <el-input-number v-model="form.sleepTime" :min="0" :max="24" :precision="1" style="width: 100%" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="饮食习惯" prop="dietHabit">
          <el-input v-model="form.dietHabit" type="textarea" :rows="2" placeholder="如：三餐规律，少油少盐" />
        </el-form-item>
        <el-form-item label="记录时间" prop="recordTime">
          <el-date-picker
            v-model="form.recordTime"
            type="datetime"
            placeholder="选择记录时间"
            value-format="YYYY-MM-DDTHH:mm:ss"
            style="width: 100%"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" :loading="submitLoading" @click="handleSubmit">确定</el-button>
      </template>
    </el-dialog>

    <!-- AI 健康建议弹窗 -->
    <el-dialog v-model="aiDialogVisible" title="AI 健康分析建议" width="640px" destroy-on-close>
      <div v-loading="aiLoading" class="ai-content">
        <template v-if="aiResult">
          <div class="ai-section">
            <div class="ai-label">
              <el-icon color="#409eff"><FirstAidKit /></el-icon>
              健康改善建议
            </div>
            <p>{{ aiResult.healthAdvice }}</p>
          </div>
          <div class="ai-section">
            <div class="ai-label">
              <el-icon color="#67c23a"><Trophy /></el-icon>
              运动建议
            </div>
            <p>{{ aiResult.exerciseAdvice }}</p>
          </div>
          <div class="ai-section">
            <div class="ai-label">
              <el-icon color="#e6a23c"><KnifeFork /></el-icon>
              饮食建议
            </div>
            <p>{{ aiResult.dietAdvice }}</p>
          </div>
        </template>
        <el-empty v-else-if="!aiLoading" description="暂无分析结果" />
      </div>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Search, Refresh, Plus, Delete, Edit, MagicStick } from '@element-plus/icons-vue'
import {
  getHealthList,
  addHealth,
  updateHealth,
  deleteHealth,
  batchDeleteHealth,
  aiSuggest
} from '@/api/health'

const loading = ref(false)
const submitLoading = ref(false)
const aiLoading = ref(false)
const tableData = ref([])
const selectedIds = ref([])
const dialogVisible = ref(false)
const aiDialogVisible = ref(false)
const dialogTitle = ref('新增记录')
const formRef = ref(null)
const aiResult = ref(null)

const queryForm = reactive({
  timeRange: null,
  minBmi: null,
  maxBmi: null
})

const pagination = reactive({
  pageNum: 1,
  pageSize: 10,
  total: 0
})

const defaultForm = () => ({
  id: null,
  height: null,
  weight: null,
  heartRate: null,
  bloodPressure: '',
  exerciseDuration: null,
  sleepTime: null,
  dietHabit: '',
  recordTime: ''
})

const form = reactive(defaultForm())

const rules = {
  height: [{ required: true, message: '请输入身高', trigger: 'blur' }],
  weight: [{ required: true, message: '请输入体重', trigger: 'blur' }],
  heartRate: [{ required: true, message: '请输入心率', trigger: 'blur' }],
  bloodPressure: [{ required: true, message: '请输入血压', trigger: 'blur' }],
  exerciseDuration: [{ required: true, message: '请输入运动时长', trigger: 'blur' }],
  sleepTime: [{ required: true, message: '请输入睡眠时间', trigger: 'blur' }],
  dietHabit: [{ required: true, message: '请输入饮食习惯', trigger: 'blur' }],
  recordTime: [{ required: true, message: '请选择记录时间', trigger: 'change' }]
}

function getBmiTagType(bmi) {
  if (!bmi) return 'info'
  if (bmi < 18.5) return 'warning'
  if (bmi <= 24) return 'success'
  if (bmi <= 28) return 'warning'
  return 'danger'
}

function buildQueryParams() {
  const params = {
    pageNum: pagination.pageNum,
    pageSize: pagination.pageSize
  }
  if (queryForm.minBmi != null) params.minBmi = queryForm.minBmi
  if (queryForm.maxBmi != null) params.maxBmi = queryForm.maxBmi
  if (queryForm.timeRange?.length === 2) {
    params.recordTimeStart = queryForm.timeRange[0]
    params.recordTimeEnd = queryForm.timeRange[1]
  }
  return params
}

async function fetchList() {
  loading.value = true
  try {
    const res = await getHealthList(buildQueryParams())
    tableData.value = res.data.records || []
    pagination.total = res.data.total || 0
  } finally {
    loading.value = false
  }
}

function handleSearch() {
  pagination.pageNum = 1
  fetchList()
}

function handleReset() {
  queryForm.timeRange = null
  queryForm.minBmi = null
  queryForm.maxBmi = null
  pagination.pageNum = 1
  fetchList()
}

function handleSelectionChange(rows) {
  selectedIds.value = rows.map((r) => r.id)
}

function handleAdd() {
  dialogTitle.value = '新增记录'
  Object.assign(form, defaultForm())
  dialogVisible.value = true
}

function handleEdit(row) {
  dialogTitle.value = '编辑记录'
  Object.assign(form, {
    id: row.id,
    height: row.height,
    weight: row.weight,
    heartRate: row.heartRate,
    bloodPressure: row.bloodPressure,
    exerciseDuration: row.exerciseDuration,
    sleepTime: row.sleepTime,
    dietHabit: row.dietHabit,
    recordTime: row.recordTime
  })
  dialogVisible.value = true
}

function resetForm() {
  formRef.value?.resetFields()
  Object.assign(form, defaultForm())
}

async function handleSubmit() {
  const valid = await formRef.value.validate().catch(() => false)
  if (!valid) return

  submitLoading.value = true
  try {
    if (form.id) {
      await updateHealth({ ...form })
      ElMessage.success('修改成功')
    } else {
      await addHealth({ ...form })
      ElMessage.success('新增成功')
    }
    dialogVisible.value = false
    fetchList()
  } finally {
    submitLoading.value = false
  }
}

async function handleDelete(row) {
  await ElMessageBox.confirm(`确定删除 ID 为 ${row.id} 的健康记录吗？`, '提示', { type: 'warning' })
  await deleteHealth(row.id)
  ElMessage.success('删除成功')
  fetchList()
}

async function handleBatchDelete() {
  await ElMessageBox.confirm(`确定批量删除选中的 ${selectedIds.value.length} 条记录吗？`, '提示', { type: 'warning' })
  await batchDeleteHealth(selectedIds.value)
  ElMessage.success('批量删除成功')
  fetchList()
}

async function handleAiSuggest(row) {
  aiResult.value = null
  aiDialogVisible.value = true
  aiLoading.value = true
  try {
    const res = await aiSuggest(row.id)
    aiResult.value = res.data
  } catch {
    aiDialogVisible.value = false
  } finally {
    aiLoading.value = false
  }
}

onMounted(() => {
  fetchList()
})
</script>

<style scoped>
.health-record-page {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.search-card,
.table-card {
  border-radius: 8px;
}

.range-sep {
  margin: 0 8px;
  color: #909399;
}

.toolbar {
  margin-bottom: 16px;
  display: flex;
  gap: 10px;
}

.pagination {
  margin-top: 16px;
  display: flex;
  justify-content: flex-end;
}

.ai-content {
  min-height: 200px;
}

.ai-section {
  margin-bottom: 20px;
  padding: 16px;
  background: #f5f7fa;
  border-radius: 8px;
}

.ai-section:last-child {
  margin-bottom: 0;
}

.ai-label {
  display: flex;
  align-items: center;
  gap: 6px;
  font-weight: 600;
  font-size: 15px;
  margin-bottom: 8px;
  color: #303133;
}

.ai-section p {
  line-height: 1.8;
  color: #606266;
  font-size: 14px;
}
</style>
