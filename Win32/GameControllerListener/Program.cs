using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using simplesocket;

namespace GameControllerListener
{
	class Program
	{
		static void Main(string[] args)
		{
			SimpleUDPListener.StartListening();
		}
	}
}
