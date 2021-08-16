import { createStore } from "vuex"
import banners from "./modules/banners"
import categories from "./modules/categories"
import bid from "./modules/bid"
import auth from "./modules/auth"

export default createStore({

    modules: {
        banners,
        categories,
        bid,
        auth
    }

})