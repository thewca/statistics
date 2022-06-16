import { ReactElement } from "react";

export interface LinkItem {
  name: string;
  href: string;
  exact: boolean;
  icon: ReactElement;
  component?: ReactElement;
  requiresLogin?: boolean;
  subItems?: LinkItem[];
}
