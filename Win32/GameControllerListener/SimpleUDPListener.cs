using System;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Windows.Forms;

namespace simplesocket
{
	public class SimpleUDPListener
	{
		private const int ListenPort = 11000;

		private static string GetLocalIPAddress()
		{
			var host = Dns.GetHostEntry(Dns.GetHostName());
			foreach (var ip in host.AddressList)
			{
				if (ip.AddressFamily == AddressFamily.InterNetwork)
				{
					return ip.ToString();
				}
			}
			throw new Exception("Local IP Address Not Found!");
		}

		public static void StartListening()
		{
			// Print Local IP Address And Port
			Console.WriteLine("====== Game Controller Setting ======");
			Console.WriteLine(string.Format("Address : {0}", GetLocalIPAddress()));
			Console.WriteLine(string.Format("Port : {0}", ListenPort));

			UdpClient listener = new UdpClient(ListenPort);
			IPEndPoint groupEP = new IPEndPoint(IPAddress.Any, ListenPort);
			try
			{
				while (true)
				{
					var buffer = listener.Receive(ref groupEP);
					var message = Encoding.ASCII.GetString(buffer, 0, buffer.Length);
					Console.WriteLine(message);
					switch (message)
					{
						case "UP":
							SendKeys.SendWait("{UP}");
							break;
						case "DOWN":
							SendKeys.SendWait("{DOWN}");
							break;
						case "LEFT":
							SendKeys.SendWait("{LEFT}");
							break;
						case "RIGHT":
							SendKeys.SendWait("{RIGHT}");
							break;
					}
				}
			}
			catch (Exception e)
			{
				Console.WriteLine(e.Message);
			}

			listener.Close();
		}
	}
}
