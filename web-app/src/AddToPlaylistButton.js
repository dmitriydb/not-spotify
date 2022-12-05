import React, { Component } from "react";
import "./AddToPlaylistButton.css"

class AddToPlaylistButton extends React.Component {


    render() {
        return (
            <>
                <div className="add-to-playlist-button">   
                    <img src="img/add-to-playlist.png"></img>
                </div>
            </>
        );
    }
}

export default AddToPlaylistButton;