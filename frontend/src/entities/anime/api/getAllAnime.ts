import {$auth_host} from "@/shared/api";
import {AxiosResponse} from "axios";
import {IAnime} from "@/entities/anime";

export const getAllAnime = async (): Promise<AxiosResponse<IAnime[]>> => {
    return await $auth_host.get('anime');
}