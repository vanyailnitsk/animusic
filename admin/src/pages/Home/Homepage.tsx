import React, {useContext, useEffect, useState} from 'react';
import {getAllAnime} from "../../services/api/anime";
import "./HomePage.css"
import {useFetching, useFetchingResult} from "../../hooks/useFetching";
import CardsList from "../../components/CardList/CardsList";
import {IAnime} from "../../models/Anime";
import {Context} from "../../index";
import {CREATE_ANIME} from "../../Forms/vatietyForms";

const Homepage = () => {
    const {contentStore} = useContext(Context)
    const [animeCards,setAnimeCards] = useState<IAnime[]>([])
    const {fetching :fetchAnime,isLoading,error} : useFetchingResult= useFetching(async () => {
        const response = await getAllAnime()
        setAnimeCards(response.data)
    })
    useEffect(() => {
        fetchAnime()
        contentStore.setCurrentContentType(CREATE_ANIME)
    }, [animeCards.length]);
    return (
        <div className="home__page__wrapper">
            <div className='logo'>
                <span>Animusic</span>
            </div>
            {!error?
                (!isLoading?
                         <CardsList animeCards={animeCards}/>
                        :null
                )
                : <div>{error}</div>
            }
        </div>
    );
};

export default Homepage;