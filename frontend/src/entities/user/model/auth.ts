import {IUser} from "@/entities/user";

export interface AuthResponse{
    accessToken:string
    refreshToken:string
    user:IUser
}