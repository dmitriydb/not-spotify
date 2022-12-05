import React, { Component } from "react";
import "./AlbumData.css"

class AlbumData extends React.Component {

    render() {
        var text = this.props.song.name? "You're listening to": "";
        var genres = "";
        if (this.props.song.name) {
            for (var genre of this.props.song.genres) {
                genres = genres + genre + ", ";
            }
        }
        if (genres.endsWith(", ")) {
            genres = genres.substr(0, genres.length - 2);
        }
        
        return (
            <>
                <div className="album-data">
                    <p className="p-songs-count" align="center">{text}</p>
                    <p align="center" className="p-album-title">{this.props.song.name}</p>
                    <p align="center" className="p-band-title">{this.props.song.band}</p>
                    <p align="center" className="genre-text">{genres}</p>
                </div>
            </>
        );
    }
}

export default AlbumData;