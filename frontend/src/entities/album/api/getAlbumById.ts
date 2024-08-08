import {IAlbum} from "@/entities/album";
import {$host} from "@/shared/api";
import {AxiosResponse} from "axios";


export const getAlbumById = async (albumId: string): Promise<AxiosResponse<IAlbum>> => {
    const response = await $host.get('albums/' + albumId);
    return response;
}

