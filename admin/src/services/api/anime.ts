import {$host} from "./index";
import {IAnime} from "../../models/Anime";

interface getAnimeInfoResponse {
    data: IAnime
}

interface createAnimeResponse {
    data: IAnime
}
interface editAnimeProps{
    id:number,
    animeData:{
        title:string
        studio:string | undefined
        releaseYer:string | undefined
        folderName:string
    }
}
interface editBannerProps{
    id:string | undefined
    banner:FormData
}
interface addCardProps {
    card: FormData
    id: number | string | undefined
}

export const getAnimeInfo = async (animeId: string | undefined): Promise<getAnimeInfoResponse> => {
    const data = await $host.get('anime/' + animeId);
    return data;
}


export const getAllAnime = async () => {
    const {data} = await $host.get('anime');
    return {data};
}

export const addAnimeCard = async (data: addCardProps) => {
    await $host.post('anime/images/card/' + data.id, data.card, {
        headers: {
            'Content-Type': 'multipart/form-data'
        }
    })
}
export const createAnime = async (animeData: string): Promise<createAnimeResponse> => {
    const response = await $host.post('anime', animeData, {
        headers: {
            "Content-Type": 'application/json'
        }
    })
    return response
}
export const editAnime = async (editAnimeData : any) => {
    await $host.put('anime/' + editAnimeData.id,JSON.stringify(editAnimeData.animeData),{
        headers:{
            "Content-Type":'application/json'
        }
    })
}
export const editAnimeBanner = async ({id,banner}:{id:string | undefined,banner:FormData}) => {
    await $host.post('anime/images/banner/' +id,banner,{
        headers:{
            "Content-Type":'multipart/form-data'
        }
    })
}