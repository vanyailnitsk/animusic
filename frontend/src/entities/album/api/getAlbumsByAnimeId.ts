import {AxiosResponse} from "axios";
import {IAlbumDto} from "@/entities/album";
import {$host} from "@/shared/api";

export const getAlbumsByAnimeId = async (id: string): Promise<AxiosResponse<IAlbumDto[]>> => {
    const response = await $host.get('albums', {
        params: {
            'animeId': id
        }
    });
    return response;
}