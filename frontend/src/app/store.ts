import {combineReducers, configureStore} from "@reduxjs/toolkit";
import {musicReducer} from "@/entities/music";

const rootReducer = combineReducers({
    music:musicReducer
})
const store = configureStore({
    reducer:rootReducer
})
export default store
