import {Image} from "./image";

export interface CoverArt {
    colors: {
        colorLight: string
        colorDark: string
    }
    image: Image
}