<template>
  <el-container class="layout-container">
    <el-aside width="220px" class="layout-aside">
      <div class="logo">
        <el-icon :size="22"><FirstAidKit /></el-icon>
        <span>健康管理</span>
      </div>
      <el-menu
        :default-active="activeMenu"
        background-color="#001529"
        text-color="#ffffffa6"
        active-text-color="#fff"
        router
      >
        <el-menu-item index="/health">
          <el-icon><Document /></el-icon>
          <span>健康记录管理</span>
        </el-menu-item>
      </el-menu>
    </el-aside>

    <el-container>
      <el-header class="layout-header">
        <div class="header-title">个人健康管理系统</div>
      </el-header>
      <el-main class="layout-main">
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { computed } from 'vue'
import { useRoute } from 'vue-router'

const route = useRoute()
const activeMenu = computed(() => route.path)
</script>

<style scoped>
.layout-container {
  height: 100vh;
}

.layout-aside {
  background-color: #001529;
  overflow: hidden;
}

.logo {
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
  color: #fff;
  font-size: 18px;
  font-weight: 600;
  border-bottom: 1px solid #ffffff1a;
}

.el-menu {
  border-right: none;
}

.layout-header {
  background: #fff;
  display: flex;
  align-items: center;
  box-shadow: 0 1px 4px rgba(0, 0, 0, 0.08);
  padding: 0 24px;
}

.header-title {
  font-size: 18px;
  font-weight: 600;
  color: #303133;
}

.layout-main {
  padding: 20px;
  background: #f0f2f5;
}
</style>
