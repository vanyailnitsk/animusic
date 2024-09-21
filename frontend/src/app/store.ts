import {combineReducers, configureStore} from "@reduxjs/toolkit";
import {musicReducer} from "@/entities/music";
import {userReducer} from "@/entities/user";

const rootReducer = combineReducers({
    music:musicReducer,
    user:userReducer
})
const store = configureStore({
    reducer:rootReducer
})
export default store
export type RootState = ReturnType<typeof store.getState>
export type AppDispatch = typeof store.dispatch