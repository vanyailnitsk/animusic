import {AxiosResponse} from "axios";
import {$host} from "@/shared/api";
import {IUser} from "@/entities/user";


export class UserService {
    static fetchUsers():Promise<AxiosResponse<IUser>>{
        return $host.get<IUser>('users')
    }
}