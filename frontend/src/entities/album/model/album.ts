import {CoverArt} from "@/shared/types";
import {ISoundtrack} from "@/entities/soundtrack";

export interface IAlbum {
    id: number
    name: string
    anime: {
        id: number
        title: string
    }
    link: string
    soundtracks: ISoundtrack[]
    coverArt: CoverArt
}

export interface IAlbumDto {
    id: number
    name: string
    coverArt: null | CoverArt
}
