import {$host} from "./index";
import {IAnime} from "../../models/Anime";
import {Album} from "../../models/Albums";
import {Playlist} from "../../models/UserPlaylists";
import {ISoundtrack} from "../../models/Soundtracks";
import {AxiosResponse} from "axios";
import {soundtrackInfo} from "../../Forms/EditSoundtrack/EditSoundtrack";
export const collection = 'collection'
interface getAlbumResponse{
    data:Album
}
interface editSoundtrackImageAudioRequestFields{
    id:number
    data:FormData
}
interface getSoundtrackInfo{
    data:ISoundtrack
}
export const getAlbumById = async (playlistId: string | undefined):Promise<getAlbumResponse> => {
    const response = await $host.get('albums/' + playlistId);
    return response;
}

export const addSoundtrack = async (data :FormData) => {
    await $host.post('soundtracks', data,{
        headers:{
            "Content-Type":'multipart/form-data'
        }
    })
}
export const getSoundtrackInfo = async (id:number):Promise<getSoundtrackInfo> => {
    const data = await $host.get('soundtracks/'+id)
    return data
}
export const editSoundtrackInfoRequest = async ({id, data}: {id:number | null, data:soundtrackInfo}) => {
    await $host.put('soundtracks/'+id,data)
}
export const editSoundtrackImageRequest = async ({id, data}: {id:number | null, data:FormData}) => {
    await $host.put('soundtracks/images/'+id,data,{
        headers:{
            "Content-Type":'multipart/form-data'
        }
    })
}
export const editSoundtrackAudioRequest = async ({id, data}: {id:number | null, data:FormData}) => {
    await $host.put('soundtracks/audio/'+id,data,{
        headers:{
            "Content-Type":'multipart/form-data'
        }
    })
}