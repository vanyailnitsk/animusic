import {IAlbums} from "./Albums";
import {Image} from "./image";

export interface IAnime{
    banner: {
        color:string,
        image: Image
    },
    cardImage : Image
    description : string | null
    folderName : string
    id : number
    albums : IAlbums[]
    releaseYear : string | null
    studio : string | null
    title : string
}
