import { useRoute, useRouter } from 'vue-router'

export function useAppRouter() {

    const router = useRouter()

    const route = useRoute()

    return {

        router,
        routeName: route.name,
        itemName: route.meta.itemName,
        reqName: route.query.category

    }

}