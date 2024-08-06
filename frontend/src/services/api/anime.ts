import {$host} from "./index";
import {IAnime} from "../../models/Anime";

interface getAnimeInfoResponse{
    data:IAnime
}
export const getAnimeInfo = async (animeId : string | undefined) : Promise<getAnimeInfoResponse> => {
    const data = await $host.get('anime/'+animeId);
    return data;
}


export const getAllAnime = async () => {
    const {data} = await $host.get('anime');
    return {data};
}


export const createAnime = async (animeData : IAnime) => {
    const {data} = await $host.post('anime/create',animeData)
    return {data}
}