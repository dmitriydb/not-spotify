import React, { Component } from "react";
import "./Menu.css";
import RecentlyPlayedItem from "./RecentlyPlayedItem";
import Modal from 'react-modal';
import "./Modal.css";
import PlayablePlaylist from "./PlayablePlaylist";

class Menu extends React.Component {

    constructor(props) {
        super(props);
        this.state = { signingUp: false, status: "", mode: "menu", wasPreloaded: false};
        this.closeSignup = this.closeSignup.bind(this);
        this.openSignup = this.openSignup.bind(this);
        this.closeSignin = this.closeSignin.bind(this);
        this.openSignin = this.openSignin.bind(this);
        this.usernameInput = React.createRef();
        this.passwordInput = React.createRef();
        this.passwordInput2 = React.createRef();
        this.validateRegisterData = this.validateRegisterData.bind(this);
        this.validateAuthData = this.validateAuthData.bind(this);
        this.processRegistrationResponse = this.processRegistrationResponse.bind(this);
        this.showPlaylists = this.showPlaylists.bind(this);
        this.getPlaylists = this.getPlaylists.bind(this);
        this.changePlaylist = this.changePlaylist.bind(this);
        this.showMenu = this.showMenu.bind(this);
        this.removePlaylist = this.removePlaylist.bind(this);
    }

    changePlaylist(playlist) {
        this.props.changeSongs(playlist);
        this.showMenu();
    }

    removePlaylist(playlist) {
        fetch('http://localhost:4000/playlist/' + playlist.id, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(response => {
            console.log(response.status);
            if (response.status == 200) {
                this.setState({ playlists: this.state.playlists.filter(p => p.id != playlist.id) });
            }
        })
    }

    showPlaylists() {
        if (!this.state.wasPreloaded && this.props.preloadedPlaylists) {
            console.log("Using preloaded playlists");
            console.log(JSON.stringify(this.props.preloadedPlaylists));
            this.setState({ mode: "playlists", playlists: this.props.preloadedPlaylists, wasPreloaded: true });
        } else {
            this.getPlaylists();
            this.setState({ mode: "playlists", playlists: [], wasPreloaded: true});
        }
        
    }

    showMenu() {
        this.setState({ mode: "menu", playlists: [] });
    }

    getPlaylists() {
        fetch('http://localhost:4000/playlist/', {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(response => response.json())
            .then(response => { this.setState({ playlists: response.filter(ex => ex.user_id == this.props.userId) }) })
    }

    validateRegisterData() {
        var username = this.usernameInput.current.value;
        var password = this.passwordInput.current.value;
        var passwordAgain = this.passwordInput2.current.value;
        if (!username) {
            this.setState({ status: "Username is empty" });
        } else if (!password || !passwordAgain) {
            this.setState({ status: "Input both passwords" });
        } else if (password != passwordAgain) {
            this.setState({ status: "Passwords should match" });
        } else {
            this.props.processRegistration({ username, password })
                .then(response => this.processRegistrationResponse(response));
        }
    }

    validateAuthData() {
        var username = this.usernameInput.current.value;
        var password = this.passwordInput.current.value;
        if (!username) {
            this.setState({ status: "Username is empty" });
        } else if (!password) {
            this.setState({ status: "Password is empty" });
        } else {
            this.props.processAuth({ username, password })
                .then(response => this.processRegistrationResponse(response));
        }
    }

    processRegistrationResponse(response) {
        if (response.status == 409) {
            this.setState({ status: "Username already exists" });
        }
        else if (response.status == 403) {
            this.setState({ status: "Wrong username/password" });
        }
        else if (response.status == 200) {
            response.json()
                .then(data => {
                    this.props.passAuthToken(data);
                    this.closeSignup();
                    this.closeSignin();
                });
        } else {
            this.setState({ status: "Error has occurred, try again please" });
        }
    }

    closeSignup() {
        this.setState({ signingUp: false });
    }

    openSignup() {
        this.setState({ signingUp: true });
    }

    closeSignin() {
        this.setState({ signingIn: false });
    }

    openSignin() {
        this.setState({ signingIn: true });
    }

    render() {
        var recently = [];
        let key = 1;
        for (var song of this.props.history) {
            recently.push(
                <RecentlyPlayedItem key={key++} song={song} changeSongCallBack={this.props.changeSongCallBack} />
            )
        }
        if (this.state.mode == "menu") {
            return (
                <>
                    <div className="menu">
                        <p className="menu-item">Home</p>
                        <p className="menu-item">Search</p>
                        {
                            !this.props.authCompleted &&
                            <>
                                <p className="menu-item" onClick={this.openSignup}>Sign up</p>
                                <p className="menu-item" onClick={this.openSignin}>Sign in</p>
                            </>
                        }
                        {
                            this.props.authCompleted &&
                            <>
                                <p className="menu-item" onClick={this.showPlaylists}>Playlists</p>
                                <p className="menu-item">Favorites</p>
                                <p className="menu-item" onClick={this.props.resetAuth}>Sign off</p>
                                <p className="greetings">{"Hi, " + this.props.username + "!"}</p>

                            </>
                        }
                        <hr></hr>
                        <p className="menu-caption">Recently played</p>
                        {recently}
                    </div>

                    <Modal
                        isOpen={this.state.signingUp}
                        onRequestClose={this.closeSignup}
                        contentLabel="Example Modal"
                        className="modal-content"
                    >
                        <label className="my-label" align="center" htmlFor="username"><b>Username</b></label>
                        <input autocomplete="chrome-off" className="my-input" type="text" placeholder="Enter username" name="username" required ref={this.usernameInput} />
                        <label className="my-label" align="center" htmlFor="psw"><b>Password</b></label>
                        <input data-lpignore="true"
                            className="my-input" type="password" placeholder="Enter password" name="psw" required ref={this.passwordInput} autocomplete="new-password" />
                        <label className="my-label" align="center" htmlFor="psw-repeat"><b>Repeat Password</b></label>
                        <input data-lpignore="true"
                            className="my-input" type="password" placeholder="Repeat password" name="psw-repeat" required ref={this.passwordInput2} autocomplete="new-password" />
                        <label className="my-label" align="center"><b>{this.state.status}</b></label>
                        <div className="buttons">
                            <button type="button" className="cancelbtn" onClick={this.closeSignup}>Cancel</button>
                            <button type="submit" className="signupbtn" onClick={this.validateRegisterData}>Sign up</button>
                        </div>
                    </Modal>
                    <Modal
                        isOpen={this.state.signingIn}
                        onRequestClose={this.closeSignin}
                        contentLabel="Example Modal"
                        className="modal-content"
                    >
                        <label className="my-label" align="center" htmlFor="username"><b>Username</b></label>
                        <input autocomplete="chrome-off" className="my-input" type="text" placeholder="Enter username" name="username" required ref={this.usernameInput} />
                        <label className="my-label" align="center" htmlFor="psw"><b>Password</b></label>
                        <input data-lpignore="true"
                            autocomplete="new-password" className="my-input" type="password" placeholder="Enter password" name="psw" required ref={this.passwordInput} />
                        <label className="my-label" align="center"><b>{this.state.status}</b></label>
                        <div className="buttons">
                            <button type="button" className="cancelbtn" onClick={this.closeSignin}>Cancel</button>
                            <button type="submit" className="signupbtn" onClick={this.validateAuthData}>Sign in</button>
                        </div>
                    </Modal>
                </>
            );
        } else {
            var content = [];
            for (var playlist of this.state.playlists) {
                content.push(
                    <PlayablePlaylist key={playlist.id} playlist={playlist} removePlaylist={this.removePlaylist} changePlaylist={this.changePlaylist}></PlayablePlaylist>
                )
            }
            content.push(
                <p className="menu-item" key={1} onClick={this.showMenu}>Back</p>
            )
            return (
                <>
                    <div className="menu">
                        {content}
                    </div>

                </>
            )
        }

    }
}

export default Menu;