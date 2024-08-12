import {IAnime} from "./Anime";

export interface AnimeCardProps {
    card: IAnime | undefined
}
export interface AnimeTestCardProps{
    title:string | undefined
    cardImage:string | null
}
export interface CardListProps {
    animeCards : IAnime[]
}
export interface AnimeNameCardProps {
    name:string
    id:number
}