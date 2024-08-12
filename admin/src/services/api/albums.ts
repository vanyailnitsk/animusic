import {$host} from "./index";
interface updateAlbumProps{
    id:string | undefined
    updateData:FormData
}
export const createAlbum = async (data :{animeId:string|undefined,name:"Openings" | "Endings" | "Scene songs" | "Themes"}) => {
    const response = await $host.post('albums',JSON.stringify(data),{
        headers:{
            "Content-Type":"application/json"
        }
    })
    return response
}
export const updateAlbum = async (data:updateAlbumProps) => {
    await $host.post('albums/cover-art/'+data.id, data.updateData,{
        headers:{
            "Content-Type":'multipart/form-data'
        }
    })
}