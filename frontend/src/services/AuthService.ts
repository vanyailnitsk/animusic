import {AxiosResponse} from "axios";
import {$auth_host} from "./api";
import {AuthResponse} from "../models/response/AuthResponse";

export default class AuthService {
    static async login(email: string, password: string) : Promise<AxiosResponse<AuthResponse>>{
        return $auth_host.post<AuthResponse>('/auth/login',JSON.stringify({email, password}), {
            headers: {
                "Content-Type": "application/json"
            }

        })
    }
    static async registration(username:string,email: string, password: string) : Promise<AxiosResponse<AuthResponse>>{
        return $auth_host.post<AuthResponse>('/auth/register', {username, email, password})
    }
    static async logout() : Promise<void>{
        return $auth_host.post('/logout')
    }
}
