import {combineReducers, configureStore} from "@reduxjs/toolkit";
import {musicReducer} from "@/entities/music";

const rootReducer = combineReducers({
    music:musicReducer
})
const store = configureStore({
    reducer:rootReducer
})
export default store
export type RootState = ReturnType<typeof store.getState>
export type AppDispatch = typeof store.dispatch