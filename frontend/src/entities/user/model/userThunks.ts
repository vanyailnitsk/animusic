import {createAsyncThunk} from "@reduxjs/toolkit";
import {ErrorType} from "@/shared/types";
import {checkAuth, login, LoginData, registration, RegistrationData} from "@/entities/user";

export const userLogin = createAsyncThunk('user/login',async (data: LoginData, thunkAPI) =>  {
    try {
        const response = await login(data.email,data.password)
        localStorage.setItem('token', response.data.accessToken)
        return response.data.user
    }
    catch (e:unknown) {
        const errorMessage = e.response?.data?.message || 'Server is unavailable or unknown error occurred';
        return thunkAPI.rejectWithValue({
            messageError:errorMessage,
        })
    }
})

export const userRegistration = createAsyncThunk('user/registration', async (data:RegistrationData, thunkAPI) => {
    try {
        const response = await registration(data.username,data.email,data.password)
        localStorage.setItem('token', response.data.accessToken)
        return response.data.user
    }
    catch (e: unknown) {
        const errorMessage = e.response?.data?.message || 'Server is unavailable or unknown error occurred';
        return thunkAPI.rejectWithValue({
            messageError:knownError.message,
            status: knownError.response?.status
        })
    }
})

export const checkUserAuth = createAsyncThunk('user/check_auth', async (_, thunkAPI) => {
    try {
        const response = await checkAuth()
        localStorage.setItem('token', response.data.accessToken)
        return response.data.user
    }
    catch (e:unknown) {
        const knownError = e as ErrorType
        return thunkAPI.rejectWithValue({
            messageError:knownError.message,
            status: knownError.response?.status
        })
    }
})