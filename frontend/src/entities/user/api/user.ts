import {$auth_host, loginUrl, refreshUrl, registerUrl} from "@/shared/api";
import {AuthResponse} from "@/entities/user";
import {AxiosResponse} from "axios";

export const login = (email: string, password: string):Promise<AxiosResponse<AuthResponse>> => {
    return $auth_host.post(loginUrl,JSON.stringify({email, password}), {
        headers: {
            "Content-Type": "application/json"
        }})
}
export const registration = (username:string,email: string, password: string): Promise<AxiosResponse<AuthResponse>> => {
    return $auth_host.post(registerUrl, {username, email, password})
}
export const checkAuth = (): Promise<AxiosResponse<AuthResponse>> => {
    return $auth_host.post(`${import.meta.env.VITE_REACT_APP_API_URL}${refreshUrl}`, {}, {withCredentials: true})
}