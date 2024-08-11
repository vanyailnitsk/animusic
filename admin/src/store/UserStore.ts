import {IUser} from "../models/IUser";
import {makeAutoObservable} from "mobx";
import AuthService from "../services/AuthService";
import axios from "axios";
import {AuthResponse} from "../models/response/AuthResponse";
import {HOME_ROUTE} from "../navigation/routes";
import {$auth_host} from "../services/api";

export default class UserStore {
    user : IUser | null = null;
    isAuth:boolean = false
    isAuthInProgress:boolean = false
    constructor() {
        makeAutoObservable(this)
    }
    setAuth(bool:boolean){
        this.isAuth = bool;
    }
    setAuthProgress(bool:boolean){
        this.isAuthInProgress =  bool
    }
    setUser(user : IUser | null){
        this.user = user
    }
    async login(email:string,password:string){
        this.setAuthProgress(true)
        try{
            if(email && password){
                const response = await AuthService.login(email,password)
                localStorage.setItem('token', response.data.accessToken)
                this.setAuth(true)
                this.setUser(response.data.user)
            }
        } catch (e : unknown){
            throw e
        } finally {
            this.setAuthProgress(false)
        }

    }
    async checkAuth(){
        this.setAuthProgress(true)
        try{
            const response = await $auth_host.post<AuthResponse>(`/auth/refresh`,{},{withCredentials:true})
            localStorage.setItem('token', response.data.accessToken)
            this.setAuth(true)
            this.setUser(response.data.user)
        } catch (e : any){
            console.log(e.response?.data?.message)
        } finally {
            this.setAuthProgress(false)
        }
    }
}