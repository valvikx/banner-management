import { createRouter, createWebHistory } from 'vue-router'
import Home from '@/views/Home.vue'

const routes = [
    {
        path: '/',
        name: 'home',
        component: Home
    },
    {
        path: '/admin',
        name: 'admin',
        component: () => import('@/views/Admin.vue')
    },
    {
        path: '/bid:category?',
        name: 'bid',
        component: () => import('@/views/Bid.vue')
    },
    {
        path: '/banners',
        name: 'banners',
        component: () => import('@/components/BannerContent.vue'),
        meta: {itemName: 'banner', requiredAuth: true}
    },
    {
        path: '/categories',
        name: 'categories',
        component: () => import('@/components/CategoryContent.vue'),
        meta: {itemName: 'category', requiredAuth: true}
    },
    {
        path: '/:pathMatch(.*)*',
        redirect: '/'

    }
]

const router = createRouter({

    history: createWebHistory(),
    routes

})

router.beforeEach((to, from, next) => {

    if (to.matched.some(record => record.meta.requiredAuth)) {

        if (!sessionStorage.auth) {

            next({path: '/admin'})

        } else {

            next()

        }

    } else {

        next()

    }

})

export default router
