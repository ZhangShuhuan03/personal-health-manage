import { createRouter, createWebHistory } from 'vue-router'
import MainLayout from '@/layout/MainLayout.vue'

const routes = [
  {
    path: '/',
    component: MainLayout,
    redirect: '/health',
    children: [
      {
        path: 'health',
        name: 'HealthRecord',
        component: () => import('@/views/HealthRecord.vue'),
        meta: { title: '健康记录管理' }
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
