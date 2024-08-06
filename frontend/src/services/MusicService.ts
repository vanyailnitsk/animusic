import {$host} from "./api";
import {collection} from "./api/tracks";


export default class MusicService{
    static addToFavorite(id:number){
        return $host.post(collection,{},{
            params:{
                trackId:id
            }
        })
}
}