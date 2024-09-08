import {createSlice, PayloadAction} from "@reduxjs/toolkit";
import {checkUserAuth, IUser, userLogin, userRegistration, UserState} from "@/entities/user";
import {ErrorType, RejectedDataType} from "@/shared/types";

const initialState: UserState = {
    user: null,
    loading: false,
    error: ''
}
const userSlice = createSlice({
    name: 'user',
    initialState,
    reducers: {
        logout: (state:UserState) => {
                localStorage.removeItem('token')
                state.user = null
                window.history.pushState(null, '', window.location.pathname)
        }
    },
    extraReducers:(builder) => {
        builder
            .addCase(userLogin.pending, (state:UserState) => {
                state.user = null
                state.error = ''
                state.loading = true
            })
            .addCase(userLogin.fulfilled, (state:UserState, action:PayloadAction<IUser>) => {
                state.user = action.payload
                state.error = ''
                state.loading = false
            })
            .addCase(userLogin.rejected,(state:UserState, action) => {
                state.user = null
                state.loading = false
                state.error = action.payload.messageError as string
            })
            .addCase(userRegistration.fulfilled, (state:UserState, action:PayloadAction<IUser>) => {
                state.user = action.payload
                state.error = ''
                state.loading = false
            })
            .addCase(userRegistration.rejected,(state:UserState, action) => {
                state.user = null
                state.loading = false
                state.error = action.payload.messageError as string
            })
            .addCase(checkUserAuth.pending,(state:UserState, action) => {
                state.loading = true
                state.error = ''
            })
            .addCase(checkUserAuth.fulfilled,(state:UserState, action:PayloadAction<IUser>) => {
                state.user = action.payload
                state.loading = false
                state.error = ''
            })
            .addCase(checkUserAuth.rejected,(state:UserState, action) => {
                state.loading = false
                state.error = action.payload.messageError
            })
}
})
export const {logout} = userSlice.actions
export default userSlice.reducer