import {$host, collection} from "@/shared/api";
import {AxiosResponse} from "axios";
import {Playlist} from "@/entities/playlist";


export class MusicService {
    static addToFavorite(id: number) {
        return $host.post(collection, {}, {
            params: {
                trackId: id
            }
        })
    }
    static removeFromFavorite(id:number){
        return $host.delete(collection, {
            params: {
                trackId: id
            }
        })
    }
    static async getCollection():Promise<AxiosResponse<Playlist>>{
        const response:AxiosResponse<Playlist> = await $host.get(collection);
        return response;
    }
}