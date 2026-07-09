import request from '@/utils/request'

/** 分页查询健康记录 */
export function getHealthList(params) {
  return request.get('/api/health/list', { params })
}

/** 根据ID查询 */
export function getHealthById(id) {
  return request.get(`/api/health/${id}`)
}

/** 新增健康记录 */
export function addHealth(data) {
  return request.post('/api/health/add', data)
}

/** 修改健康记录 */
export function updateHealth(data) {
  return request.put('/api/health/update', data)
}

/** 单条删除 */
export function deleteHealth(id) {
  return request.delete(`/api/health/${id}`)
}

/** 批量删除 */
export function batchDeleteHealth(ids) {
  return request.delete('/api/health/batch', { data: ids })
}

/** AI健康分析 */
export function aiSuggest(recordId) {
  return request.post('/api/health/ai/suggest', { recordId })
}
