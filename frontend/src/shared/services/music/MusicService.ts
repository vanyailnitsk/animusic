import {$host, collection} from "@/shared/api";


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
}