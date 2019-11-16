package ControllersBoundaries;

import java.io.Serializable;
import java.util.Date;

import CineplexClasses.Cinema;
import CineplexClasses.Cineplex;
import CineplexClasses.CineplexGroup;
import CineplexClasses.Movie;
import CineplexClasses.Show;


public class AdminManager extends Manager implements Serializable
{
	private static final long serialVersionUID = 1L;
		
	// Cinema Hall
    public void createCinema (CineplexGroup cineplexGroup, int cineplexID, String name, int num_rows, int [] column, Cinema.ClassType classtype)
    {
    	cineplexGroup.getCineplexList().get(cineplexID).addCinemaToList(name, num_rows, column, classtype);   
    }
    
    public void removeCinema (CineplexGroup cineplexGroup, int cineplexID, int cinemaID)
    {
    	if (cineplexID>=0 && cineplexID<cineplexGroup.getCineplexList().size() && cinemaID>=0 && cinemaID<cineplexGroup.getCineplexList().get(cineplexID).getCinemaList().size())
    		cineplexGroup.getCineplexList().get(cineplexID).removeCinemaFromlist(cinemaID);
        else
        	System.out.println("CineplexGroup -- removeCinema -- " + cineplexID + cinemaID);
    }
    
    // Cineplex
    public void addCineplexToList(CineplexGroup cineplexGroup, String newCineplexName) 
    {
    	cineplexGroup.getCineplexList().add(new Cineplex(cineplexGroup.getCineplexList().size(), newCineplexName));
    }
    
    public void removeCineplexFromlist(CineplexGroup cineplexGroup, int cineplexID) 
    {
        if (cineplexID>=0 && cineplexID<cineplexGroup.getCineplexList().size())
        	cineplexGroup.getCineplexList().remove(cineplexID);
        else
        	System.out.println("Invalid CineplexID");
    }
    
    // Shows
    
    public void createShow (CineplexGroup cineplexGroup, int cineplexID, int cinemaID, int movieID, int time_start, int time_end, Show.DayType daytype, Date show_date)
    {
    	if (time_start>=time_end || 0>time_start || time_start>2400 || 0>time_end || time_end>2400)
    	{
    		System.out.println ("Invalid Time");
			return;
    	}
    	
    	for (Show otherShow: cineplexGroup.getShowList(cineplexID))
    	{
    		if (cinemaID == otherShow.getHall().getHallId()) // same hall
    		{
    			if (!((otherShow.getTime_end()<=time_start)||(otherShow.getTime_start()>=time_end))) // time clash
    			{
    				// Assumption : shows happen only between 0000 hours and 2400 hours on the same day
    				System.out.println ("Time Slot Clash for given Cinema");
    				return;
    			}		
    		}
    	}
    	
    	cineplexGroup.getCineplexList().get(cineplexID).addShowToList(cineplexGroup.getMovieList().get(movieID), cinemaID, time_start, time_end, daytype, show_date);
    }
    
    public void deleteShow (CineplexGroup cineplexGroup, int cineplexID, int showID)
    {
    	cineplexGroup.getCineplexList().get(cineplexID).removeShowFromlist(showID);
    }
    
    public void printShowLayout (CineplexGroup cineplexGroup, int cineplexID, int showID)
    {
    	cineplexGroup.getCineplexList().get(cineplexID).getShowList().get(showID).getLayout().printSeats();
    }
    
    // Movie
    
    public void addMovieToList(CineplexGroup cineplexGroup, String title, String synopsis, String[]cast, String director, Movie.Genre genre, Movie.Type type) 
    {    	
    	cineplexGroup.getMovieList().add(new Movie(cineplexGroup.getMovieList().size(), title, synopsis, cast, director, genre, type));
//    	cineplexGroup.addMovieToList(title, synopsis, cast, director, genre, type);
//    	System.out.println(title + "|" +cineplexGroup.getMovieList().size());
    }
    
    public void removeMovieFromlist(CineplexGroup cineplexGroup, int movieID) 
    {
        if (movieID>=0 && movieID<cineplexGroup.getMovieList().size())
        	cineplexGroup.getMovieList().remove(movieID);
        else
        	System.out.println("Invalid MovieID");
    }   
    
    public void updateCinemaName(CineplexGroup cineplexGroup, String name, int cplexID, int cinemaID) {
		cineplexGroup.getCineplexList().get(cplexID).getCinemaList().get(cinemaID).setName(name);
	}
	public void updateCinemaRows(CineplexGroup cineplexGroup,int rows, int cplexID, int cinemaID) {
		cineplexGroup.getCineplexList().get(cplexID).getCinemaList().get(cinemaID).setNumRows(rows);
	}
	public void updateCinemaSeatsPerLane(CineplexGroup cineplexGroup,int[] seats, int cplexID, int cinemaID) {
		cineplexGroup.getCineplexList().get(cplexID).getCinemaList().get(cinemaID).setColumn(seats);
	}
	public void updateCinemaClassType(CineplexGroup cineplexGroup,int option, int cplexID, int cinemaID) {
		cineplexGroup.getCineplexList().get(cplexID).getCinemaList().get(cinemaID).setClasstype(Cinema.ClassType.values()[option-1]);
	}
	public void updateShowHall(CineplexGroup cineplexGroup, int cineplexID, int cinemaID, int showID) {
		Cinema new_hall = cineplexGroup.getCineplexList().get(cineplexID).getCinemaList().get(cinemaID);
		cineplexGroup.getCineplexList().get(cineplexID).getShowList().get(showID).changeHall(new_hall);
	}
	public void updateShowStartTime(CineplexGroup cineplexGroup, int cineplexID, int cinemaID, int showID, int time_start) {
		cineplexGroup.getCineplexList().get(cineplexID).getShowList().get(showID).setTime_start(time_start);
	}
	public void updateShowEndTime(CineplexGroup cineplexGroup, int cineplexID, int cinemaID, int showID, int time_end) {
		cineplexGroup.getCineplexList().get(cineplexID).getShowList().get(showID).setTime_end(time_end);
	}
	public void updateShowDayType(CineplexGroup cineplexGroup, int cineplexID, int cinemaID, int showID, int option) {
		cineplexGroup.getCineplexList().get(cineplexID).getShowList().get(showID).setDaytype(Show.DayType.values()[option-1]);
	}
	public void updateShowDate(CineplexGroup cineplexGroup, int cineplexID, int cinemaID, int showID, Date show_date) {
		cineplexGroup.getCineplexList().get(cineplexID).getShowList().get(showID).setShow_date(show_date);
	}

    public void updateMovieTitle(CineplexGroup cineplexGroup,int movieID, String title) {
        cineplexGroup.getMovieList().get(movieID).setTitle(title);
    }

    public void updateMovieShowingStatus(CineplexGroup cineplexGroup,int movieID,Movie.ShowingStatus showingStatus) {
    	cineplexGroup.getMovieList().get(movieID).setShowingStatus(showingStatus);
    }

    public void updateMovieSynopsis(CineplexGroup cineplexGroup,int movieID,String synopsis) {
    	cineplexGroup.getMovieList().get(movieID).setSynopsis(synopsis);
    }
    
    public void updateMovieDirector(CineplexGroup cineplexGroup,int movieID,String director) {
    	cineplexGroup.getMovieList().get(movieID).setDirector(director);
    }

    public void updateMovieCast(CineplexGroup cineplexGroup,int movieID,String[] cast) {
    	cineplexGroup.getMovieList().get(movieID).setCast(cast);
    }

    public void updateMovieType(CineplexGroup cineplexGroup,int movieID, Movie.Type type) {
    	cineplexGroup.getMovieList().get(movieID).setType(type);
    }

    public void updateMovieGenre(CineplexGroup cineplexGroup,int movieID,Movie.Genre genre) {
    	cineplexGroup.getMovieList().get(movieID).setGenre(genre);
    }
    
}

