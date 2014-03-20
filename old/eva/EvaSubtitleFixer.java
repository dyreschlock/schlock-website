import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;


public class EvaSubtitleFixer
{
	public static final String ORIGINAL_LOCATION = "c:/subtitles.srt";
	public static final String FIXED_LOCATION = "c:/subtitles_fixed.srt";
	
	//time lines in the form of xx:xx:xx,xxx --> xx:xx:xx,xxx
	public static final String TIME_REGEX = "[0-9][0-9]:[0-9][0-9]:[0-9][0-9],[0-9][0-9][0-9]";
	public static final String TIME_LINE_REGEX = TIME_REGEX + " --> " + TIME_REGEX;
	
	public static final int OFFSET_BY_SECONDS = 32;
	
	public EvaSubtitleFixer()
	{
	}
	
	public void run() throws Exception
	{
		File original = new File(ORIGINAL_LOCATION);
		File fixed = new File(FIXED_LOCATION);
		
		if(!fixed.createNewFile())
		{
			throw new RuntimeException("Could not create fixed file.");
		}
		
		BufferedReader reader = new BufferedReader(new FileReader(original));
		BufferedWriter writer = new BufferedWriter(new FileWriter(fixed));
		
		String line = reader.readLine();
		int lineNumber = 0;
		while(line != null)
		{
			if(isTimeLine(line))
			{
				writer.write(offsetLine(line, lineNumber));
			}
			else
			{
				writer.write(line);
			}
			writer.newLine();
			
			line = reader.readLine();
			lineNumber++;
		}
		
		reader.close();
		writer.close();
	}
	
	private boolean isTimeLine(String line)
	{
		return line.matches(TIME_LINE_REGEX);
	}
	
	private String offsetLine(String line, int lineNumber)
	{
		String[] times = line.split(" ");
		
		if(times.length != 3)
		{
			throw new RuntimeException("Time code is malformed on " + lineNumber + ". [" + line + "]");
		}
		
		String time1 = offsetTime(times[0]);
		String time2 = offsetTime(times[2]);
		
		return time1 + " " + times[1] + " " + time2;
	}
	
	private String offsetTime(String line)
	{
		String[] time = line.split(":");
		
		int hr = Integer.parseInt(time[0]);
		int min = Integer.parseInt(time[1]);
		
		String[] seconds = time[2].split(",");
		
		int sec = Integer.parseInt(seconds[0]);
		String millis = seconds[1];
		
		sec += OFFSET_BY_SECONDS;
		if(sec >= 60)
		{
			sec -= 60;
			min += 1;
		}
		if(min >= 60)
		{
			min -= 60;
			hr += 1;
		}
		
		String hour = Integer.toString(hr);
		if(hour.length() == 1)
		{
			hour = "0" + hour;
		}
		String minute = Integer.toString(min);
		if(minute.length() == 1)
		{
			minute = "0" + minute;
		}
		String second = Integer.toString(sec);
		if(second.length() == 1)
		{
			second = "0" + second;
		}
		
		return hour + ":" + minute + ":" + second + "," + millis;
	}
	
	public static void main(String[] args) throws Exception
	{
		new EvaSubtitleFixer().run();
	}
}
