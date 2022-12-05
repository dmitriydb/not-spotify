import React, { Component } from "react";
import "./Menu.css";
import RecentlyPlayedItem from "./RecentlyPlayedItem";


class Menu extends React.Component {

    render() {
        var recently = [];
        for (var song of this.props.history) {
            recently.push(
               <RecentlyPlayedItem song={song} changeSongCallBack={this.props.changeSongCallBack}/>
            )
        }
        return (
            <>
                <div className="menu">
                    <p className="menu-item">Home</p>
                    <p className="menu-item">Search</p>
                    <p className="menu-item">Playlists</p>
                    <p className="menu-item">Favorites</p>
                    <hr></hr>
                    <p className="menu-caption">Recently played</p>
                    {recently}
                </div>
            </>
        );
    }
}

export default Menu;