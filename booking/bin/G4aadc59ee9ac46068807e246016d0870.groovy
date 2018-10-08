
				WebDriver driver = r2d2.getWrappedObject();
				WFLogger.createLogger(log);

				log.info("Start to create object of the Credentials");
				def credentialsObject = credentials.getWrappedObject();
				log.info("Object of the class Creentials successfully created");

				//It's not common thing, please don't use it
				ArrayList<String> emails;
				ArrayList<String> rooms;
				def row = data.get(data.size()-1);  //last row in datastore with emails, names
				log.info("Start to read initial data for Search module");
				emails = setEmails(row);
				rooms = setRooms(row);
				log.info("reading initial data for Search module finished successfully");
				def timeRow = timeData.get(timeData.size()-1); //last row in datastore with times
				String fromTime = timeRow.get("from_time");
				String fromDate = timeRow.get("from_date");
				String tillDate = timeRow.get("till_date");
				String eventFirstStartTime = timeRow.get("event_first_start_time");
				String eventFinishTime = timeRow.get("event_finish_time");
				log.info("Start to create object of the FormatterDates");
				def formatterDates = formatter.getWrappedObject();
				log.info("Object of the class FormatterDates successfully created");
				formatterDates.setInstance(log, credentialsObject);
				log.info("log was initilized");
				//end of me


				log.info("Start to create object of the Utils");
				def utilsObject = utils.getWrappedObject();
				log.info("Object of the class Utils successfully created");
				utilsObject.setInctance(driver, log);
				log.info("Driver in the Utils was initilized");



				log.info("Create object of the LoginPageActions");
				def loginPageActions = login_page_actions.getWrappedObject();
				log.info("Object of the class LoginPageActions successfully created");
				loginPageActions.setInstance(driver, log, utilsObject, credentialsObject);
				log.info("Driver and logger in the LoginPageActions were initilized");

				log.info("Create object of the MainPageActions");
				def mainPageActions = main_page_actions.getWrappedObject();
				log.info("Object of the class MainPageActions successfully created");
				mainPageActions.setInstance(driver, log, utilsObject, credentialsObject);
				log.info("Driver and logger and utils object in the MainPageActions were initilized");

				log.info("Create object of the CalendarPageActions");
				def calendarPageActions = calendar_page_actions.getWrappedObject();
				log.info("Object of the class CalendarPageActions successfully created");
				calendarPageActions.setInstance(driver, log, utilsObject, credentialsObject);
				log.info("Driver and logger and utils object in theCalendarPageActions were initilized");

				log.info("Create object of the EventPageActions");
				def eventPageActions = event_page_actions.getWrappedObject();
				log.info("Object of the class EventPageActions successfully created");
				eventPageActions.setInstance(driver, log, utilsObject, credentialsObject);
				log.info("Driver and logger and utils object in the EventPageActions were initilized");

				log.info("Create object of the AttendesPageActions");
				def attendesPageActions = attendes_page_actions.getWrappedObject();
				log.info("Object of the class  AttendesPageActions successfully created");
				attendesPageActions.setInstance(driver, log, utilsObject, credentialsObject);
				log.info("Driver and logger and utils object in the  AttendesPageActions were initilized");
				attendesPageActions.setEmailsAndRooms(emails, rooms);

				//webdriver execution
				loginPageActions.enterMail();
				mainPageActions.pressCalendarIcon();
				calendarPageActions.addEvent();
				eventPageActions.addAttendee();
				attendesPageActions.fillAttendeeFileld();
				LinkedHashMap result = eventPageActions.findTimeRange(rooms,  fromTime, fromDate, tillDate, eventFirstStartTime, eventFinishTime);
				//end of execution
				
				LinkedList formattedResult = formatterDates.refactorData(result,  fromTime);
				log.info("FINAL RESULT AS a HashMap:"+formattedResult);

				json = new com.google.gson.Gson().toJson(formattedResult);
				log.info("FINAL RESULT AS a JSON:"+json);
				
				//For better view in a manual task
				String outputData = replaceRoomTitles(json, credentialsObject);
				log.info("FINAL RESULT AS The replaced String:"+outputData);

				// isSearchSuccessful is a flag for next bot, Values: -1 == JSON empty, 0 == JSON.size()>0
				int isSearchSuccessful=-1;
				if (json.size()>0) {
					isSearchSuccessful=0;
				}

				//For better view in a manual task
				private String replaceRoomTitles(def json, def credentialsObject) {
					String roomsFormatted = json.toString();
					roomsFormatted = roomsFormatted.replaceAll(credentialsObject.ROOM_BARBECUE, credentialsObject.ROOM1_SHORT_NAME);
					roomsFormatted = roomsFormatted.replaceAll(credentialsObject.ROOM_RED, credentialsObject.ROOM2_SHORT_NAME);
					roomsFormatted = roomsFormatted.replaceAll(credentialsObject.ROOM_YELLOW, credentialsObject.ROOM3_SHORT_NAME);
				}

				sys.defineVariable("field_json", outputData);
				sys.defineVariable("flag_well_json", isSearchSuccessful);

				private ArrayList setEmails(def row) {
					log.info ("datastoreValues"+row);
					String textEmail = row.get("persons_emails").toString();
					String[] array = textEmail.split(";");
					emails = new ArrayList();
					for (int i=0; i<array.length; i++) {
						emails.add(array[i]);
					}
					return emails;
				}

				private ArrayList setRooms(def row) {
					String textRoom = row.get("persons_names").toString();
					String[] array = textRoom.split(";");
					rooms = new ArrayList();
					for (int i=0; i<array.length; i++) {
						rooms.add(array[i]);
					}
					return rooms;
				}
			