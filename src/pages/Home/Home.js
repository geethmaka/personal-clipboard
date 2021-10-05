import React from 'react';
import './Home.css';
import Card from '@mui/material/Card';


import { createTheme } from '@material-ui/core/styles';
import { ThemeProvider } from "@material-ui/core/styles";
import { Typography, TextField, Button, Stack, Grid } from '@mui/material';

const darkTheme = createTheme({
  palette: {
     mode: 'dark',
  },
});

const Home = (props) =>{
    return(
        <div>
            
            <TextField
                id="outlined-multiline-static"
                label="Multiline"
                multiline
                rows={4}
                defaultValue="Default Value"
            />

            <div className="btn-stack-1">
                <Stack spacing={2} direction="row">
                    <Button variant="contained">Contained</Button>
                    <Button variant="contained">Contained</Button>
                </Stack>
            </div>
        </div>
    );
}

export default Home;