import {createAsyncThunk} from "@reduxjs/toolkit";
import {addToCollection, getCollection, removeFromCollection} from "@/entities/music";
import {ErrorType} from "@/shared/types";


export const fetchCollection = createAsyncThunk("collection/fetchCollection", async (arg, thunkAPI) => {
    try {
        const response = await getCollection()
        return response.soundtracks
    }
    catch (e:unknown){
        const knownError = e as ErrorType

        return thunkAPI.rejectWithValue({
            messageError: knownError.message,
            status: knownError.response?.status,
        })
    }
})
export const addTrackToCollection = createAsyncThunk("collection/addTrackToCollection", async (id:number, thunkAPI) => {
    try {
        await addToCollection(id)
        return id
    }
    catch (e:unknown){
        const knownError = e as ErrorType

        return thunkAPI.rejectWithValue({
            messageError: knownError.message,
            status: knownError.response?.status,
        })
    }
})
export const removeTrackFromCollection = createAsyncThunk("collection/removeTrackFromCollection", async (id:number, thunkAPI) => {
    try {
        await removeFromCollection(id)
        return id
    }
    catch (e:unknown){
        const knownError = e as ErrorType

        return thunkAPI.rejectWithValue({
            messageError: knownError.message,
            status: knownError.response?.status,
        })
    }
})