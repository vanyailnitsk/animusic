import {$auth_host} from "@/shared/api";
import {IAnime} from "@/entities/anime";
import {AxiosResponse} from "axios";


export const getAnimeInfo = async (animeId: string): Promise<AxiosResponse<IAnime>> => {
    const response: AxiosResponse<IAnime> = await $auth_host.get('anime/' + animeId);
    return response;
}