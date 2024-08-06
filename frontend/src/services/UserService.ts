import {$host} from "./api";
import {IUser} from "../models/IUser";
import {AxiosResponse} from "axios";


export default class UserService{
    static fetchUsers():Promise<AxiosResponse<IUser>>{
        return $host.get<IUser>('users')
    }
}