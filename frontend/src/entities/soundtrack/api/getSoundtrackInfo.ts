import {$host, soundtracks} from "@/shared/api";
import {AxiosResponse} from "axios";
import {ISoundtrack} from "@/entities/soundtrack";


export const getSoundtrackInfo = async (id:number):Promise<AxiosResponse<ISoundtrack>> => {
    const response = await $host.get(soundtracks+id)
    return response
}