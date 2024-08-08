import "./home-page.css"
import {AnimeCardsList} from "@/widgets/anime-card-list";
import {Logo} from "@/shared/ui";

export const HomePage = () => {
    return (
        <div className="home__page__wrapper">
            <Logo/>
            <AnimeCardsList/>
        </div>
    );
};

