import {ISoundtrack} from "./Soundtracks";
import {CoverArt} from "./CoverArt";

export interface IAlbums {
    id:number
    name:string
    coverArt:CoverArt
}
export interface AlbumsProps {
    albums:IAlbums[]
    handleNavigate(albumId : number) : void
}
export interface AlbumCardProps{
    name:string
    id: number
    image:string
    handleNavigate(albumId : number):void
}
export interface Album {
    id:number
    name:string
    anime: {
        id:number
        title:string
    }
    link:string
    soundtracks:ISoundtrack[]
    coverArt:CoverArt
}