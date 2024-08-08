import {Image} from "@/shared/types";
import {IAlbum} from "@/entities/album";

export interface IAnime{
    banner: {
        color:string,
        image: Image
        e
    },
    cardImage : Image
    description : string | null
    folderName : string
    id : number
    albums: IAlbum[]
    releaseYear : number
    studio : string | null
    title : string
}