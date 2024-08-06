import React, {useEffect, useState} from 'react';
import {getAllAnime} from "../../services/api/anime";
import "./HomePage.css"
import {useFetching, useFetchingResult} from "../../hooks/useFetching";
import CardsList from "../../components/CardList/CardsList";
import {IAnime} from "../../models/Anime";
import SkeletonCardList from "../../components/CardList/Skeleton/SkeletonCardList";

const Homepage = () => {
    const [animeCards,setAnimeCards] = useState<IAnime[]>([])
    const {fetching :fetchAnime,isLoading,error} : useFetchingResult= useFetching(async () => {
        const response = await getAllAnime()
        setAnimeCards(response.data)
    })
    useEffect(() => {
        fetchAnime()
    }, []);
    return (
        <div className="home__page__wrapper">
            <div className='logo'>
                <span>Animusic</span>
            </div>
            {!error?
                (!isLoading?
                         <CardsList animeCards={animeCards}/>
                        :<SkeletonCardList/>
                )
                : <div>{error}</div>
            }
        </div>
    );
};

export default Homepage;