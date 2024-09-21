export interface IUser{
    id:number
    username:string
    email:string
    enabled:boolean
    authorities:string | null
}

export interface UserState{
    user:IUser | null
    loading:boolean
    error: string
}

export interface AuthResponse{
    accessToken:string
    user:IUser
}
export interface LoginData{
    email:string
    password:string
}
export interface RegistrationData{
    username:string
    email:string
    password:string
}