import React from 'react';
import './App.css';
import Home from './pages/Home/Home';
import {
  BrowserRouter as Router,
  Switch,
  Route,
} from "react-router-dom";

import IconButton from '@mui/material/IconButton';
import Box from '@mui/material/Box';
import { useTheme, ThemeProvider, createTheme } from '@mui/material/styles';

import Brightness4Icon from '@mui/icons-material/Brightness4';
import Brightness7Icon from '@mui/icons-material/Brightness7';

import Card from '@mui/material/Card';
import { Button, Typography } from '@mui/material';

const ColorModeContext = React.createContext({ toggleColorMode: () => {} });

function NavBar() {
  const theme = useTheme();
  const colorMode = React.useContext(ColorModeContext);
  return (
    <div style={{display: "flex"}}>
    {/* <Box
      sx={{
        display: 'flex',
        width: '100%',
        height: '100%',
        // alignItems: 'center',
        // justifyContent: 'center',
        bgcolor: 'background.default',
        color: 'text.primary',
        borderRadius: 1,
        p: 3,
      }}
    > */}
      

      {/* <Card> */}
        <Typography variant='h1' className="topic1" color="text.primary">Personal-Clipboard</Typography>
      {/* </Card>  */}

      <Button onClick={colorMode.toggleColorMode} className="colorModeButton"sx={{height:"2.5rem"}}>
        {theme.palette.mode} mode
        <IconButton sx={{ ml: 1 }} color="inherit">
          {theme.palette.mode === 'dark' ? <Brightness7Icon /> : <Brightness4Icon />}
        </IconButton>
      </Button>
      
    {/* </Box> */}
    </div>
  );
}




export default function App() {
  const [mode, setMode] = React.useState('light');
  const colorMode = React.useMemo(
    () => ({
      toggleColorMode: () => {
        setMode((prevMode) => (prevMode === 'light' ? 'dark' : 'light'));
      },
    }),
    [],
  );

  const theme = React.useMemo(
    () =>
      createTheme({
        palette: {
          mode,
        },
      }),
    [mode],
  );

  return (
    <div className="App" style={{height:"100%"}}>
      <ColorModeContext.Provider value={colorMode} sx={{height:"100%"}}>
        <ThemeProvider theme={theme} sx={{height:"50rem"}}>
          <Box sx={{
                width: '100%',
                height: '100%',
                // alignItems: 'center',
                // justifyContent: 'center',
                bgcolor: 'background.default',
                color: 'text.primary',
                borderRadius: 1,
                p: 3,
              }}
            >

            <NavBar />
            
            <Router>
              <Switch>
                <Route exact path="/" component={Home}/>
              </Switch>
            </Router>

          </Box>
        </ThemeProvider>
      </ColorModeContext.Provider>
    </div>
  );
}




