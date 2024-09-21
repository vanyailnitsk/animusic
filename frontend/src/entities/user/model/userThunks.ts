import {createAsyncThunk} from "@reduxjs/toolkit";
import {KnownErrorType, RejectedDataType} from "@/shared/types";
import {checkAuth, IUser, login, LoginData, registration, RegistrationData} from "@/entities/user";

export const userLogin = createAsyncThunk<IUser, LoginData, {readonly rejectValue: RejectedDataType}>('user/login',async (data: LoginData, thunkAPI) =>  {
    try {
        const response = await login(data.email,data.password)
        localStorage.setItem('token', response.data.accessToken)
        return response.data.user
    }
    catch (e:unknown) {
        const errorMessage = e as KnownErrorType;
        return thunkAPI.rejectWithValue({
            messageError:errorMessage.response?.data?.message || 'Server is unavailable or unknown error occurred',
        })
    }
})

export const userRegistration = createAsyncThunk<IUser, RegistrationData, { readonly rejectValue: RejectedDataType }>('user/registration', async (data:RegistrationData, thunkAPI) => {
    try {
        const response = await registration(data.username,data.email,data.password)
        localStorage.setItem('token', response.data.accessToken)
        return response.data.user
    }
    catch (e: unknown) {
        const errorMessage = e as KnownErrorType;
        return thunkAPI.rejectWithValue({
            messageError:errorMessage.response?.data?.message || 'Server is unavailable or unknown error occurred',
        })
    }
})

export const checkUserAuth = createAsyncThunk<IUser, void, {readonly rejectValue:RejectedDataType}>('user/check_auth', async (_, thunkAPI) => {
    try {
        const response = await checkAuth()
        localStorage.setItem('token', response.data.accessToken)
        return response.data.user
    }
    catch (e:unknown) {
        const knownError = e as KnownErrorType
        return thunkAPI.rejectWithValue({
            messageError:knownError.response?.data?.message || 'Server is unavailable or unknown error occurred',
        })
    }
})