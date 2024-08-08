import {$host} from "@/shared/api";
import {AxiosResponse} from "axios";
import {IAnime} from "@/entities/anime";

export const getAllAnime = async (): Promise<AxiosResponse<IAnime[]>> => {
    const response = await $host.get('anime');
    return response;
}