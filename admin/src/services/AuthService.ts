import {AxiosResponse} from "axios";
import {$auth_host} from "./api";
import {AuthResponse} from "../models/response/AuthResponse";

export default class AuthService {
    static async login(email: string, password: string) : Promise<AxiosResponse<AuthResponse>>{
        return $auth_host.post('/auth/login',JSON.stringify({email, password}), {
            headers: {
                "Content-Type": "application/json"
            }

        })
    }
}
