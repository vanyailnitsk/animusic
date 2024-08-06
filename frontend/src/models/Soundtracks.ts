import {IAlbums} from "./Albums";
import {PlaylistSoundtrack} from "./UserPlaylists";
import {Image} from "./image";

export interface ISoundtrack {
    soundtrack: SoundtrackData
}
export interface SoundtrackListProps {
    soundtracks: ISoundtrack[] | PlaylistSoundtrack[]
}
export interface SoundtrackData{
    id: number
    animeTitle:string
    audioFile:string
    duration:number
    image: Image | null
    originalTitle:string
    saved:boolean
    album:IAlbums
    anime:{
        id:number
        title:string
    }

}
export interface SoundtrackProps{
    soundtrackData: SoundtrackData
    listening_queue: ISoundtrack[]
    index:number
}